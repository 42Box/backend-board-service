package com.practice.boxboardservice.service.boards.service_boards;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
import com.practice.boxboardservice.entity.likes.ServiceBoardsDislikesEntity;
import com.practice.boxboardservice.entity.likes.ServiceBoardsLikesEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.repository.boards.service_boards.ServiceBoardsRepository;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageConditionDto;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageResultDto;
import com.practice.boxboardservice.repository.likes.service_boards_likes.ServiceBoardsDislikesRepository;
import com.practice.boxboardservice.repository.likes.service_boards_likes.ServiceBoardsLikesRepository;
import com.practice.boxboardservice.service.aws.s3.S3Service;
import com.practice.boxboardservice.service.aws.s3.dto.S3UploadDto;
import com.practice.boxboardservice.service.aws.s3.dto.S3UploadResultDto;
import com.practice.boxboardservice.service.aws.s3.type.S3File;
import com.practice.boxboardservice.service.boards.service_boards.dto.GetServiceBoardsDto;
import com.practice.boxboardservice.service.boards.service_boards.dto.PostServiceBoardsDto;
import com.practice.boxboardservice.service.boards.service_boards.dto.UpdateServiceBoardsDto;
import com.practice.boxboardservice.service.dto.DeleteBoardsDto;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * ServiceBoardsService.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Service
public class ServiceBoardsService {

  private final ServiceBoardsRepository serviceBoardsRepository;
  private final ServiceBoardsLikesRepository likesRepository;
  private final ServiceBoardsDislikesRepository dislikesRepository;

  private final S3Service s3Service;
  private final ModelMapper modelMapper;
  private final EnvUtil envUtil;

  public ServiceBoardsService(ServiceBoardsRepository serviceBoardsRepository,
      ServiceBoardsLikesRepository likesRepository,
      ServiceBoardsDislikesRepository dislikesRepository, S3Service s3Service,
      ModelMapper modelMapper, EnvUtil envUtil) {
    this.serviceBoardsRepository = serviceBoardsRepository;
    this.likesRepository = likesRepository;
    this.dislikesRepository = dislikesRepository;
    this.s3Service = s3Service;
    this.modelMapper = modelMapper;
    this.envUtil = envUtil;
  }


  public void postBoards(PostServiceBoardsDto dto) {
    S3UploadDto s3UploadDto = new S3UploadDto(dto.getImageFile(), S3File.SERVICE_BOARD_IMAGE);
    S3UploadResultDto s3UploadResultDto = new S3UploadResultDto();
    if (dto.getImageFile() != null) {
      s3UploadResultDto = s3Service.uploadFile(s3UploadDto);
    } else {
      s3UploadResultDto.setPath("");
    }
    try {
      dto.setImagePath(s3UploadResultDto.getPath());
      ServiceBoardsEntity serviceBoardsEntity = createServiceBoardsFromPostDto(dto);
      serviceBoardsRepository.save(serviceBoardsEntity);
    } catch (Exception e) {
      if (dto.getImageFile() != null) {
        s3Service.deleteFile(s3UploadResultDto.getPath());
      }
      throw new DefaultServiceException("boards.error.post-fail", envUtil);
    }
  }

  private ServiceBoardsEntity createServiceBoardsFromPostDto(PostServiceBoardsDto dto) {
    return ServiceBoardsEntity.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .serviceUrl(dto.getServiceUrl())
        .imagePath(dto.getImagePath())
        .writerUuid(dto.getWriterUuid())
        .writerNickname(dto.getWriterNickname())
        .writerProfileImagePath(dto.getWriterProfileImagePath())
        .build();
  }

  public GetServiceBoardsDto findBoardsByBoardId(Long boardId, Optional<String> userUuid) {
    ServiceBoardsEntity serviceBoardsEntity = serviceBoardsRepository.findByIdAndDeleted(boardId,
            false)
        .orElseThrow(() -> new DefaultServiceException("boards.error.not-found", envUtil));
    GetServiceBoardsDto resultDto = modelMapper.map(serviceBoardsEntity, GetServiceBoardsDto.class);
    resultDto.setBoardId(serviceBoardsEntity.getId());
    setBoardLiked(resultDto, userUuid);
    setBoardDisliked(resultDto, userUuid);
    return resultDto;
  }

  private void setBoardLiked(GetServiceBoardsDto resultDto, Optional<String> userUuid) {
    if (userUuid.isPresent()) {
      Optional<ServiceBoardsLikesEntity> entity = likesRepository
          .getByBoardIdAndUserUuid(resultDto.getBoardId(), userUuid.get());
      if (entity.isPresent()) {
        resultDto.setBoardLiked(true);
      }
      return;
    }
    resultDto.setBoardLiked(false);
  }

  private void setBoardDisliked(GetServiceBoardsDto resultDto, Optional<String> userUuid) {
    if (userUuid.isPresent()) {
      Optional<ServiceBoardsDislikesEntity> entity = dislikesRepository
          .getByBoardIdAndUserUuid(resultDto.getBoardId(), userUuid.get());
      if (entity.isPresent()) {
        resultDto.setBoardDisliked(true);
      }
      return;
    }
    resultDto.setBoardDisliked(false);
  }

  @Transactional
  public void updateBoards(UpdateServiceBoardsDto dto, MultipartFile imageFile) {
    ServiceBoardsEntity serviceBoardsEntity = serviceBoardsRepository.findByIdAndDeleted(
            dto.getBoardId(), false)
        .orElseThrow(() -> new DefaultServiceException("boards.error.not-found", envUtil));
    if (!dto.getWriterUuid().equals(serviceBoardsEntity.getWriterUuid())) {
      throw new DefaultServiceException("global.error.forbidden", envUtil);
    }
    String filePath = serviceBoardsEntity.getImagePath();
    if ((serviceBoardsEntity.getImagePath() != null) && !serviceBoardsEntity.getImagePath()
        .isEmpty()) {
      s3Service.deleteFile(serviceBoardsEntity.getImagePath());
    }
    filePath = "";
    if (imageFile != null) {
      S3UploadDto s3UploadDto = new S3UploadDto(imageFile, S3File.SERVICE_BOARD_IMAGE);
      S3UploadResultDto s3UploadResultDto = s3Service.uploadFile(s3UploadDto);
      filePath = s3UploadResultDto.getPath();
    }
    serviceBoardsEntity.update(dto, filePath);
    serviceBoardsRepository.save(serviceBoardsEntity);
  }

  @Transactional
  public void deleteBoards(DeleteBoardsDto dto) {
    ServiceBoardsEntity serviceBoardsEntity = serviceBoardsRepository.findByIdAndDeleted(
            dto.getBoardId(), false)
        .orElseThrow(() -> new DefaultServiceException("boards.error.not-found", envUtil));
    if (!dto.getWriterUuid().equals(serviceBoardsEntity.getWriterUuid())) {
      throw new DefaultServiceException("global.error.forbidden", envUtil);
    }
    serviceBoardsEntity.delete();
    if ((serviceBoardsEntity.getImagePath() != null) && !serviceBoardsEntity.getImagePath()
        .isEmpty()) {
      s3Service.deleteFile(serviceBoardsEntity.getImagePath());
    }
    serviceBoardsEntity.deletePath();
    serviceBoardsRepository.save(serviceBoardsEntity);
  }


  public Page<ServiceBoardsPageResultDto> findBoards(Pageable pageable,
      ServiceBoardsPageConditionDto conditionDto) {
    return serviceBoardsRepository.findBoardsPage(pageable, conditionDto);
  }

  public void newServiceBoardComment(long boardId) {
    ServiceBoardsEntity serviceBoardsEntity = serviceBoardsRepository.findByIdAndDeleted(boardId,
        false).orElseThrow(NoSuchElementException::new);
    serviceBoardsEntity.addCommentCount();
    serviceBoardsRepository.save(serviceBoardsEntity);
  }

  public void deleteServiceBoardComment(long boardId) {
    ServiceBoardsEntity serviceBoardsEntity = serviceBoardsRepository.findByIdAndDeleted(boardId,
        false).orElseThrow(NoSuchElementException::new);
    serviceBoardsEntity.decreaseCommentCount();
    serviceBoardsRepository.save(serviceBoardsEntity);
  }
}
