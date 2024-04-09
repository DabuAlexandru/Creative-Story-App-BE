package com.example.masterthesisbe.service;

import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.model.FileInstance;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.repository.FileInstanceRepository;
import com.example.masterthesisbe.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileInstanceService {
    private final FileInstanceRepository fileInstanceRepository;
    private final StorageService storageService;
    private final AuthenticationService authenticationService;

    public String generateUniqueFilename(String originalFilename) {
        String uniqueIdentifier = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return uniqueIdentifier + fileExtension;
    }

    public FileInstance userUploadFile(MultipartFile file) {
        User user = authenticationService.getLoggedInUser();
        String uniqueFilename = user.getId() + "-" + generateUniqueFilename(Objects.requireNonNull(file.getOriginalFilename()));

        storageService.store(file, uniqueFilename);
        FileInstance newFileInstance = new FileInstance(uniqueFilename, user);

        return fileInstanceRepository.save(newFileInstance);
    }

    public void deleteFile(String filename) {
        User user = authenticationService.getLoggedInUser();
        FileInstance fileInstance = fileInstanceRepository.findByFileName(filename);
        if(user.getId() != fileInstance.getUser().getId()) {
            throw new ApiException("You don't have permission to delete this file!");
        }
        fileInstanceRepository.deleteByFileName(filename);
        storageService.delete(filename);
    }
}
