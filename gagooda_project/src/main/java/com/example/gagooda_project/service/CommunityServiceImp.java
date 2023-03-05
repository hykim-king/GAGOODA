package com.example.gagooda_project.service;

import com.example.gagooda_project.StaticMethods;
import com.example.gagooda_project.dto.CommunityDto;
import com.example.gagooda_project.dto.ImageDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;
import com.example.gagooda_project.mapper.CommunityMapper;
import com.example.gagooda_project.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityServiceImp implements CommunityService{
    private CommunityMapper communityMapper;
    private ImageMapper imageMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public CommunityServiceImp(CommunityMapper communityMapper, ImageMapper imageMapper){
        this.communityMapper = communityMapper;
        this.imageMapper = imageMapper;
    }

    @Override
    public List<CommunityDto> communityList(PagingDto paging) {
        paging.setRows(16);
        int totalRows = communityMapper.count(paging);
        paging.setTotalRows(totalRows);
        return communityMapper.listAll(paging);
    }

    @Override
    public CommunityDto selectOne(int commId) {
        return communityMapper.findById(commId);
    }

    @Override
    @Transactional
    public int update(List<MultipartFile> imgFileList, CommunityDto community, String imgPath, List<String> imgToDeleteList) {
        List<ImageDto> imageDeleteList = new ArrayList<>();
        log.info("imgFileList service: "+imgFileList);
        try{
            if(communityMapper.updateOne(community)<=0) throw new Error("포스트 수정 중 오류가 생겼습니다.");
            if(imgToDeleteList != null){
                for(String imgValue : imgToDeleteList){
                    String imgCode = imgValue.split("/")[0];
                    int seq = Integer.parseInt(imgValue.split("/")[1]);
                    imageDeleteList.add(imageMapper.findByImgCodeAndSeq(imgCode, seq));
                    imageMapper.deleteByImgCodeAndSeq(imgCode, seq);
                }
            }

            List<ImageDto> imageList = imageMapper.listByImgCode(community.getImgCode());
            imageMapper.deleteByImgCode(community.getImgCode());

            if(imgFileList != null){
               for(int imgFile=0; imgFile < imgFileList.size(); imgFile++){
                    ImageDto image = StaticMethods.parseIntoImage(imgFileList.get(imgFile),community.getImgCode(),imgPath+"/community",imgFile + 1);
                    if(image != null){
                        imageList.add(image);
                    }else{
                        log.error("이미지 파일이 아닙니다!!");
                    }
                }

                for(int i = 0; i < imageList.size(); i++){
                    imageList.get(i).setSeq(i+1);
                    if(imageMapper.insertOne(imageList.get(i))<= 0) throw new Error("이미지 순번을 넣는데 문제가 생겼습니다.");
                }
            }



            for(ImageDto deletedImage : imageDeleteList){
                File file = new File(imgPath + "/community/"+deletedImage.getImgPath());
                boolean del = file.delete();
                log.info(deletedImage.getImgPath()+"삭제 완료: "+del);

            }
            return 1;

        }catch(Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

    @Override
    @Transactional
    public int register(List<MultipartFile> imgFileList, CommunityDto community, String imgPath) {
        System.out.println("communityService에 입성했습니다.");
        try{
            System.out.println("try catch문 안에 들어와 있습니다.");

            System.out.println("community id: "+community.getCommId());
            System.out.println("community getImgCode: "+community.getImgCode());
            if(communityMapper.insertOne(community) <= 0) throw new Error("포스트 등록에 문제가 생겼습니다.");
            community.setImgCode("community"+community.getCommId());
            if(communityMapper.updateOne(community)<=0) throw new Error("포스트 이미지 코드를 수정하는데 문제가 있었습니다.");
            System.out.println("communityService에서 insert를 이미지 코드 update까지 완료하였습니다.");
            for(int imgFile=0; imgFile < imgFileList.size(); imgFile++){
                System.out.println("imgFileList imageFile: "+imgFileList.get(imgFile));
                if(imgFileList.get(imgFile) != null){
                    ImageDto image = StaticMethods.parseIntoImage(imgFileList.get(imgFile), community.getImgCode(), imgPath + "/community",imgFile+1);
                    if(imageMapper.insertOne(image) <= 0) throw new Error("사진을 올리는데에 문제가 생겼습니다.");
                }
            }
            System.out.println("communityService에서 image insert까지 하였습니다.");
            return 1;
        }catch(Exception | Error e){
            e.printStackTrace();
            throw new Error("커뮤니티 저장하다가 생긴 오류");
        }
    }
}
