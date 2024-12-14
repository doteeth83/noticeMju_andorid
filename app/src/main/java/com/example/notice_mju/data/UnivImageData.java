package com.example.notice_mju.data;

import com.example.notice_mju.models.UnivImage;
import java.util.ArrayList;
import java.util.List;

public class UnivImageData {
        public static List<UnivImage> getUnivImages() {
            List<UnivImage> univImages = new ArrayList<>();
            univImages.add(new UnivImage(1, "새로", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/saero.jpg", "총학생회", null));
            univImages.add(new UnivImage(2, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/liberalArts.jpg", "인문대학", null));
            univImages.add(new UnivImage(3, "울림", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/socialScience.jpg", "사회과학대학", null));
            univImages.add(new UnivImage(4, "IN:sight", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/business.jpg", "경영대학", null));
            univImages.add(new UnivImage(5, "With", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/law.jpg", "법과대학", null));
            univImages.add(new UnivImage(6, "새솔", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/ict.jpg", "ICT융합대학", null));
            univImages.add(new UnivImage(7, "화연", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/korean.jpg", "국어국문학과", "인문대학"));
            univImages.add(new UnivImage(8, "WAVE", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/english.jpg", "영어영문학과", "인문대학"));
            univImages.add(new UnivImage(9, "천하중문", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/chinese.jpg", "중어중문학과", "인문대학"));
            univImages.add(new UnivImage(10, "영원일문", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/japanese.jpg", "일어일문학과", "인문대학"));
            univImages.add(new UnivImage(11, "작심사일", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/history.jpg", "사학과", "인문대학"));
            univImages.add(new UnivImage(12, "리브리스", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/lis.jpg", "문헌정보학과", "인문대학"));
            univImages.add(new UnivImage(13, "누리", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/arab.jpg", "아랍지역학과", "인문대학"));
            univImages.add(new UnivImage(14, "리버티", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/art.jpg", "미술사학과", "인문대학"));
            univImages.add(new UnivImage(15, "격동", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/philosophy.jpg", "철학과", "인문대학"));
            univImages.add(new UnivImage(16, "창조문창", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/cw.jpg", "문예창작학과", "인문대학"));
            univImages.add(new UnivImage(17, "P.Astel", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/administration.jpg", "행정학과", "사회과학대학"));
            univImages.add(new UnivImage(18, "X", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/political.jpg", "정치외교학과", "사회과학대학"));
            univImages.add(new UnivImage(19, "마음", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/child.jpg", "아동학과", "사회과학대학"));
            univImages.add(new UnivImage(20, "Do:Dream", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/youth.jpg", "청소년지도학과", "사회과학대학"));
            univImages.add(new UnivImage(21, "DOT(Digitalmedia On Top)", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/digital.jpg", "디지털미디어학과", "사회과학대학"));
            univImages.add(new UnivImage(22, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/economics.jpg", "경제학과", "사회과학대학"));
            univImages.add(new UnivImage(23, "크레딧", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/businessDept.jpg", "경영학과", "경영대학"));
            univImages.add(new UnivImage(24, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/trade.jpg", "국제통상학과", "경영대학"));
            univImages.add(new UnivImage(25, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/businessInfo.jpg", "경영정보학과", "경영대학"));
            univImages.add(new UnivImage(26, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/convergence.jpg", "융합소프트웨어학부", "ICT융합대학"));
            univImages.add(new UnivImage(27, "ID(Insight & Design)", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/dcd.jpg", "디지털콘텐츠디자인학과", "ICT융합대학"));
            return univImages;
        }
}

