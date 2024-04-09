package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.fileInstance.FileInstanceResponseDto;
import com.example.masterthesisbe.model.FileInstance;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class FileInstanceMapper {
    public FileInstanceResponseDto convertToResponseDto(FileInstance fileInstance) {
        if (isNull(fileInstance)) {
            return null;
        }

        return new FileInstanceResponseDto(
                fileInstance.getFileName(),
                fileInstance.getUser().getId()
        );
    }
}
