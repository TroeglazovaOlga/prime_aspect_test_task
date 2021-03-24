package io.primeaspect.csvparser.dto.response;
import io.primeaspect.csvparser.model.Data;

import java.util.List;
import java.util.Objects;

public class DataListResponse {
    private List<Data> resultList;

    public DataListResponse(List<Data> resultList) {
        this.resultList = resultList;
    }

    public List<Data> getList() {
        return resultList;
    }

    public void setList(List<Data> resultList) {
        this.resultList = resultList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataListResponse that = (DataListResponse) o;
        return Objects.equals(resultList, that.resultList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultList);
    }

    @Override
    public String toString() {
        return "DataListResponse{" +
                "resultList=" + resultList +
                '}';
    }
}