package com.februy.shop.common.enumeration.conversion;

import com.februy.shop.common.enumeration.product.ProductType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 1:55
 */
public class ProductTypeTypeHandler implements TypeHandler<ProductType> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, ProductType productType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,productType.getCode());
    }

    @Override
    public ProductType getResult(ResultSet resultSet, String s) throws SQLException {
        return ProductType.getByCode(resultSet.getInt(s));
    }

    @Override
    public ProductType getResult(ResultSet resultSet, int i) throws SQLException {
        return ProductType.getByCode(resultSet.getInt(i));
    }

    @Override
    public ProductType getResult(CallableStatement callableStatement, int i) throws SQLException {
        return ProductType.getByCode(callableStatement.getInt(i));
    }
}
