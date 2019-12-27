package com.februy.shop.common.enumeration.conversion;

import com.februy.shop.common.enumeration.message.MessageStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 1:47
 */
public class MessageStatusTypeHandler implements TypeHandler<MessageStatus> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, MessageStatus messageStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,messageStatus.getCode());
    }

    @Override
    public MessageStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return MessageStatus.getByCode(resultSet.getInt(s));
    }

    @Override
    public MessageStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return MessageStatus.getByCode(resultSet.getInt(i));
    }

    @Override
    public MessageStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        return MessageStatus.getByCode(callableStatement.getInt(i));
    }
}
