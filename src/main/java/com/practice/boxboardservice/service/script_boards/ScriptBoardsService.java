package com.practice.boxboardservice.service.script_boards;

import com.practice.boxboardservice.entity.script_boards.ScriptBoardsEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.repository.script_boards.ScriptBoardsRepository;
import com.practice.boxboardservice.repository.script_boards.dto.ScriptBoardsPageConditionDto;
import com.practice.boxboardservice.repository.script_boards.dto.ScriptBoardsPageResultDto;
import com.practice.boxboardservice.service.aws.s3.S3Service;
import com.practice.boxboardservice.service.aws.s3.dto.S3UploadDto;
import com.practice.boxboardservice.service.aws.s3.dto.S3UploadResultDto;
import com.practice.boxboardservice.service.aws.s3.type.S3File;
import com.practice.boxboardservice.service.dto.DeleteBoardsDto;
import com.practice.boxboardservice.service.dto.UpdateBoardsDto;
import com.practice.boxboardservice.service.script_boards.dto.GetScriptBoardsDto;
import com.practice.boxboardservice.service.script_boards.dto.PostScriptBoardsDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * ScriptBoardsService.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Service
@AllArgsConstructor
public class ScriptBoardsService {

  private final ScriptBoardsRepository scriptBoardsRepository;
  private final S3Service s3Service;
  private final ModelMapper modelMapper;
  private final EnvUtil envUtil;


  public void postBoards(PostScriptBoardsDto dto) {
    S3UploadDto s3UploadDto = new S3UploadDto(dto.getScriptFile(), S3File.SCRIPT);
    S3UploadResultDto s3UploadResultDto = s3Service.uploadFile(s3UploadDto);
    try {
      dto.setScriptUrl(s3UploadResultDto.getUrl());
      dto.setScriptPath(s3UploadResultDto.getPath());
      ScriptBoardsEntity scriptBoardsEntity = createScriptBoardsFromPostDto(dto);
      scriptBoardsRepository.save(scriptBoardsEntity);
    } catch (Exception e) {
      s3Service.deleteFile(s3UploadResultDto.getPath());
      throw new DefaultServiceException("boards.error.post-fail", envUtil);
    }
  }

  private ScriptBoardsEntity createScriptBoardsFromPostDto(PostScriptBoardsDto dto) {
    return ScriptBoardsEntity.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .scriptName(dto.getScriptName())
        .scriptPath(dto.getScriptPath())
        .scriptUrl(dto.getScriptUrl())
        .writerUuid(dto.getWriterUuid())
        .writerNickname(dto.getWriterNickname())
        .writerProfileImageUrl(dto.getWriterProfileImageUrl())
        .writerProfileImagePath(dto.getWriterProfileImagePath())
        .build();
  }

  public GetScriptBoardsDto findBoardsByBoardId(Long boardId) {
    ScriptBoardsEntity scriptBoardsEntity = scriptBoardsRepository.findByIdAndDeleted(boardId,
            false)
        .orElseThrow(() -> new DefaultServiceException("boards.error.not-found", envUtil));
    GetScriptBoardsDto resultDto = modelMapper.map(scriptBoardsEntity, GetScriptBoardsDto.class);
    resultDto.setBoardId(scriptBoardsEntity.getId());
    return resultDto;
  }

  public void updateBoards(UpdateBoardsDto dto) {
    ScriptBoardsEntity scriptBoardsEntity = scriptBoardsRepository.findByIdAndDeleted(
            dto.getBoardId(), false)
        .orElseThrow(() -> new DefaultServiceException("boards.error.not-found", envUtil));
    if (!dto.getWriterUuid().equals(scriptBoardsEntity.getWriterUuid())) {
      throw new DefaultServiceException("global.error.forbidden", envUtil);
    }
    scriptBoardsEntity.update(dto);
    scriptBoardsRepository.save(scriptBoardsEntity);
  }

  public void deleteBoards(DeleteBoardsDto dto) {
    ScriptBoardsEntity scriptBoardsEntity = scriptBoardsRepository.findByIdAndDeleted(
            dto.getBoardId(), false)
        .orElseThrow(() -> new DefaultServiceException("boards.error.not-found", envUtil));
    if (!dto.getWriterUuid().equals(scriptBoardsEntity.getWriterUuid())) {
      throw new DefaultServiceException("global.error.forbidden", envUtil);
    }
    scriptBoardsEntity.delete();
    scriptBoardsRepository.save(scriptBoardsEntity);
  }


  public Page<ScriptBoardsPageResultDto> findBoards(Pageable pageable,
      ScriptBoardsPageConditionDto conditionDto) {
    return scriptBoardsRepository.findScriptBoardsPage(pageable, conditionDto);
  }
}
