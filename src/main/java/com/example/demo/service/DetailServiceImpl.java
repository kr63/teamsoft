package com.example.demo.service;

import com.example.demo.repository.DetailRepository;
import com.example.demo.entity.Detail;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements DetailService {

    private final DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public void deleteDetailById(Long id) {
        detailRepository.deleteById(id);
    }

    @Override
    public void deleteDetail(Detail detail) {
        detailRepository.delete(detail);
    }
}
