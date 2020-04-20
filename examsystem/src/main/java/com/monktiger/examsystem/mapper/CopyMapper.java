package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import org.springframework.stereotype.Repository;

/**
 * TbCopyDAO继承基类
 */
@Repository
public interface CopyMapper extends MyBatisBaseDao<Copy, Integer> {
}