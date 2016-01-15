package com.sinoiov.vehicle.dao;

import com.sinoiov.vehicle.dao.domain.SimpleCode;
import com.sinoiov.vehicle.dao.domain.SimpleCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SimpleCodeMapper {
    int countByExample(SimpleCodeExample example);

    int deleteByExample(SimpleCodeExample example);

    int deleteByPrimaryKey(String id);

    int insert(SimpleCode record);

    int insertSelective(SimpleCode record);

    List<SimpleCode> selectByExample(SimpleCodeExample example);

    SimpleCode selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SimpleCode record, @Param("example") SimpleCodeExample example);

    int updateByExample(@Param("record") SimpleCode record, @Param("example") SimpleCodeExample example);

    int updateByPrimaryKeySelective(SimpleCode record);

    int updateByPrimaryKey(SimpleCode record);
}