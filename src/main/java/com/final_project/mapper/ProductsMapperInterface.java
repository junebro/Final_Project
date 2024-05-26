package com.final_project.mapper;

import com.final_project.dto.ProductsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductsMapperInterface {
    @Select(" SELECT \n" +
                    "\tROW_NUMBER() OVER (ORDER BY PROCD) AS Seq,\n" +
                    "    A.*, \n" +
                    "\tCONVERT(b.pifimg1 USING utf8) AS pifimg1, \n" +
                    "\tCONVERT(b.pifimg2 USING utf8) AS pifimg2, \n" +
                    "\tCONVERT(b.pifimg3 USING utf8) AS pifimg3,\n" +
                    "\tb.PIFTT, b.PIFCAL, b.PIFNA, b.PIFTAN, b.PIFSU, b.PIFFAT, b.PIFTRF, b.PIFSAT, b.PIFCLT, b.PIFPRT,\n" +
                    "    CASE WHEN C.crtcd IS NULL THEN 0 ELSE 1 END CRTCK\n" +
                    " FROM TPRO A\n" +
                    " LEFT OUTER JOIN TPIF B ON A.PROCD = B.PROCD\n" +
                    " LEFT OUTER JOIN tcrt C ON A.procd = C.crtcd AND C.mbrno = #{userNo}\n" +
                    " WHERE A.PROTP = #{protp};" )
    List<ProductsDTO> SelectAll(@Param("userNo") String userNo, @Param("protp") int protp);
}