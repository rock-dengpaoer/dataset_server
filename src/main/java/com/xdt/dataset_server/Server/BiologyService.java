package com.xdt.dataset_server.Server;

import com.xdt.dataset_server.entity.BiologyAllName;

public interface BiologyService {
    BiologyAllName getAllNameBySpeciesUuid(String uuid);
}
