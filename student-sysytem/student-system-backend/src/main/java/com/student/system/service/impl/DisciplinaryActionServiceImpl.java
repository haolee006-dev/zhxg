package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.DisciplinaryAction;
import com.student.system.mapper.DisciplinaryActionMapper;
import com.student.system.service.DisciplinaryActionService;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaryActionServiceImpl extends ServiceImpl<DisciplinaryActionMapper, DisciplinaryAction> implements DisciplinaryActionService {
}
