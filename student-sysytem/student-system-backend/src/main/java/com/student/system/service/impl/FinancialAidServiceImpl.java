package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.FinancialAid;
import com.student.system.mapper.FinancialAidMapper;
import com.student.system.service.FinancialAidService;
import org.springframework.stereotype.Service;

@Service
public class FinancialAidServiceImpl extends ServiceImpl<FinancialAidMapper, FinancialAid> implements FinancialAidService {
}
