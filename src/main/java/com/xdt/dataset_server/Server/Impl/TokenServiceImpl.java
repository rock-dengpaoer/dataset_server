package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.TokenDao;
import com.xdt.dataset_server.Server.TokenService;
import com.xdt.dataset_server.entity.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author XDT
 * @ClassName TokenServiceImpl
 * @Description: TODO
 * @Date 2023/2/28 9:50
 **/
@Service("TokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Override
    public boolean insertToken(TokenInfo tokenInfo) {
        try {
            tokenDao.insertToken(tokenInfo);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
