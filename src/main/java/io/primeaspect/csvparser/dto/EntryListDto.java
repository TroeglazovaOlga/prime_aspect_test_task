package io.primeaspect.csvparser.dto;

import java.util.List;

public class EntryListDto {
    private List<EntryDto> resultList;

    public EntryListDto(List<EntryDto> resultList) {
        this.resultList = resultList;
    }

    public List<EntryDto> getList() {
        return resultList;
    }

    public void setList(List<EntryDto> resultList) {
        this.resultList = resultList;
    }
}