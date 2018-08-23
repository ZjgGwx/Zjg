package estore.dao;

import java.util.List;

import estore.domain.PCD;

public interface IPCDDao {

	public List<PCD> findPCDByPid(String pid);

}
