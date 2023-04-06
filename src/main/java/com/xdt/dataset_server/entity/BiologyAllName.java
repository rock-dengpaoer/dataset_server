package com.xdt.dataset_server.entity;

import lombok.*;

/**
 * @author XDT
 * @ClassName BiologyAllName
 * @Description: TODO
 * @Date 2023/4/6 13:24
 **/
@Getter
@Setter
public class BiologyAllName
{
    private String classLatinName;
    private String familyLatinName;
    private String orderLatinName;
    private String speciesLatinName;
    private String speciesLatinUuid;

    @Override
    public String toString() {
        return classLatinName + '_' +
                orderLatinName + '_' +
                familyLatinName + '_' +
                speciesLatinName;
    }
}
