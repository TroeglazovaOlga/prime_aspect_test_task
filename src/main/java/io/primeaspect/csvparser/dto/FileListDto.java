package io.primeaspect.csvparser.dto;

import java.util.List;

public class FileListDto {
    private List<FileDto> resultList;

    public FileListDto(List<FileDto> resultList) {
        this.resultList = resultList;
    }

    public List<FileDto> getList() {
        return resultList;
    }

    public void setList(List<FileDto> resultList) {
        this.resultList = resultList;
    }
}