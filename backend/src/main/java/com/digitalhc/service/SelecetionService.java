package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.mapper.SelectionMapper;
import com.digitalhc.repository.SelecetionRepository;

@Service
public class SelecetionService {
    
    private final SelectionMapper selectionMapper;
    private final SelecetionRepository selecetionRepository;

    public SelecetionService(SelectionMapper selectionMapper, SelecetionRepository selecetionRepository){
        this.selectionMapper = selectionMapper;
        this.selecetionRepository = selecetionRepository;
    }
}