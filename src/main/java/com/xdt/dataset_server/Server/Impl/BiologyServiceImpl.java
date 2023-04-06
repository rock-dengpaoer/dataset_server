package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.BiologyDao;
import com.xdt.dataset_server.Server.BiologyService;
import com.xdt.dataset_server.entity.BiologyAllName;
import org.springframework.stereotype.Service;

/**
 * @author XDT
 * @ClassName BiologyDaoServiceImpl
 * @Description: TODO
 * @Date 2023/4/6 13:32
 **/
@Service
public class BiologyServiceImpl implements BiologyService {

    private final BiologyDao biologyDao;

    public BiologyServiceImpl(BiologyDao biologyDao) {
        this.biologyDao = biologyDao;
    }

    @Override
    public BiologyAllName getAllNameBySpeciesUuid(String uuid) {
        return biologyDao.getAllNameBySpeciesUuid(uuid);
    }
}
