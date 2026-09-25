package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.SelectionRequest;
import com.digitalhc.DTO.response.SelectionResponse;
import com.digitalhc.model.Selection;

@Component
public class SelectionMapper {
    
    public Selection toEntity(SelectionRequest request){

        Selection selection = new Selection();
        selection.setMessage(request.getMessage());

        return selection;
    }

    public SelectionResponse toMapResponse(Selection selection){
        return new SelectionResponse(selection.getStatus(),
                                     selection.getMessage());
    }
}