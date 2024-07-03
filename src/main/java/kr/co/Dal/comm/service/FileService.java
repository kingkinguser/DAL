package kr.co.Dal.comm.service;

import kr.co.Dal.comm.mapper.FileMapper;
import kr.co.Dal.comm.model.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    @Transactional
    public void saveFiles(final int bardId, final List<FileRequest> files) {
        if(CollectionUtils.isEmpty(files)) {
            return;
        }

        for(FileRequest file : files) {
            file.setBardId(bardId);
        }

        fileMapper.saveAll(files);
    }


}
