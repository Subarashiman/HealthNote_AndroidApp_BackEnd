//package HealthNote.healthnote;
//
//import HealthNote.healthnote.domain.Member;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.Data;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Transactional
//public class Test {
//    @PersistenceContext
//    private EntityManager em;
//    public void memberSave(Member member){
//        em.persist(member);
//    }
//
//
//    @PostMapping("/member/join")
//    public MemberDto memberJoin(@RequestBody JoinForm joinform){
//        Member member = new Member();
//        member.setUserName(joinform.userName);
//        member.setUserId(joinform.userId);
//        member.setUserPass(joinform.userPass);
//        member.setIntroduction(joinform.introduction);
//        member.setEmail(joinform.email);
//        member.setUserImage(joinform.userImg);
//
//        memberSave(member);
//
//        return new MemberDto(true,200,"성공!");
//    }
//
//
//    @Data
//    public static class MemberDto{
//        private boolean success;
//        private int code;
//        private String successMessage;
//        public MemberDto(boolean success, int code, String successMessage) {
//            this.success = success;
//            this.code = code;
//            this.successMessage = successMessage;
//        }
//    }
//    @Data       //@Getter/@Setter, @ToString, @EqualsAndHashCode, @RequiredArgConstructor
//    public class JoinForm{
//        private String userName;
//        private String userId;
//        private String userPass;
//        private String introduction;
//        private String email;
//        private byte[] userImg;
//    }
//
//}
