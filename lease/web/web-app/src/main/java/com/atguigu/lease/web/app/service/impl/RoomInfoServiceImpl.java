package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.ApartmentInfoService;
import com.atguigu.lease.web.app.service.BrowsingHistoryService;
import com.atguigu.lease.web.app.service.RoomInfoService;
import com.atguigu.lease.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.app.vo.attr.AttrValueVo;
import com.atguigu.lease.web.app.vo.fee.FeeValueVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.atguigu.lease.web.app.vo.room.RoomDetailVo;
import com.atguigu.lease.web.app.vo.room.RoomItemVo;
import com.atguigu.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(page, queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {

        String key = RedisConstant.APP_ROOM_PREFIX + id;
        RoomDetailVo roomDetailVo = (RoomDetailVo)redisTemplate.opsForValue().get(key);

        if (roomDetailVo == null) {
            RoomInfo roomInfo = roomInfoMapper.selectById(id);
            if (roomInfo == null) {
                return null;
            }

            ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(roomInfo.getApartmentId());

            List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);

            List<AttrValueVo> attrValueList = attrValueMapper.selectListByRoomId(id);

            List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);

            List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);

            List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);

            List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(roomInfo.getApartmentId());

            List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);

            roomDetailVo = new RoomDetailVo();
            BeanUtils.copyProperties(roomInfo, roomDetailVo);
            roomDetailVo.setApartmentItemVo(apartmentItemVo);
            roomDetailVo.setGraphVoList(graphVoList);
            roomDetailVo.setAttrValueVoList(attrValueList);
            roomDetailVo.setFacilityInfoList(facilityInfoList);
            roomDetailVo.setLabelInfoList(labelInfoList);
            roomDetailVo.setPaymentTypeList(paymentTypeList);
            roomDetailVo.setFeeValueVoList(feeValueVoList);
            roomDetailVo.setLeaseTermList(leaseTermList);
            redisTemplate.opsForValue().set(key, roomDetailVo);
        }

        // 保存浏览历史
        browsingHistoryService.saveHistory(LoginUserHolder.getLoginUser().getUserId(), id);

        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> page, Long id) {
        return roomInfoMapper.pageItemByApartmentId(page, id);
    }
}






