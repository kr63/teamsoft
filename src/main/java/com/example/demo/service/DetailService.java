package com.example.demo.service;

import com.example.demo.entity.Detail;

public interface DetailService {

    void deleteDetailById(Long id);

    void deleteDetail(Detail detail);
}
