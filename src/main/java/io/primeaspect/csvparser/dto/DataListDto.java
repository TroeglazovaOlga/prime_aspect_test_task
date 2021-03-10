package io.primeaspect.csvparser.dto;

import java.util.List;

public class DataListDto {
    private List<DataDto> resultList;

    public DataListDto(List<DataDto> resultList) {
        this.resultList = resultList;
    }

    public List<DataDto> getList() {
        return resultList;
    }

    public void setList(List<DataDto> resultList) {
        this.resultList = resultList;
    }
}