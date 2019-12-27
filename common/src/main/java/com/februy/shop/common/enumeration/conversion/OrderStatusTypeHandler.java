package com.februy.shop.common.enumeration.conversion;

import com.februy.shop.common.enumeration.order.OrderStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 1:43
 */
public class OrderStatusTypeHandler implements TypeHandler<OrderStatus> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, OrderStatus orderStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,orderStatus.getCode());
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return OrderStatus.getByCode(resultSet.getInt(s));
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return OrderStatus.getByCode(resultSet.getInt(i));
    }

    @Override
    public OrderStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        return OrderStatus.getByCode(callableStatement.getInt(i));
    }
}
