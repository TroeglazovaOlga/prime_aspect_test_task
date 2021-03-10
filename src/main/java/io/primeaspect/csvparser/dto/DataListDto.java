package io.primeaspect.csvparser.dto;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public class DataListDto {
    private List<Data> resultList;

    public DataListDto(List<Data> resultList) {
        this.resultList = resultList;
    }

    public List<Data> getList() {
        return resultList;
    }

    public void setList(List<Data> resultList) {
        this.resultList = resultList;
    }
}