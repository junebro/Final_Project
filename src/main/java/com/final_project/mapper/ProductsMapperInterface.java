package com.final_project.mapper;

import com.final_project.entity.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductsMapperInterface {
    @Select(" SELECT \n" +
            " A.*, \n" +
            "\tCONVERT(b.pifimg1 USING utf8) AS pifimg1, \n" +
            "\tCONVERT(b.pifimg2 USING utf8) AS pifimg2, \n" +
            "\tCONVERT(b.pifimg3 USING utf8) AS pifimg3,\n" +
            "\tb.PIFTT, b.PIFCAL, b.PIFNA, b.PIFTAN, b.PIFSU, b.PIFFAT, b.PIFTRF, b.PIFSAT, b.PIFCLT, b.PIFPRT\n" +
            " FROM TPRO A\n" +
            " LEFT OUTER JOIN TPIF B ON A.PROCD = B.PROCD;\n")
    List<Products> SelectAll();
}
