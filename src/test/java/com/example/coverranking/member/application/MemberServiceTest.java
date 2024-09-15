//package com.example.coverranking.member.application;
//
//import com.example.coverranking.member.domain.Member;
//import com.example.coverranking.member.domain.MemberRepository;
//import com.example.coverranking.member.dto.request.AddMemberRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class MemberServiceTest {
//
//    @InjectMocks
//    private MemberService memberService;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("사용자 가입 성공")
//    void testJoinSuccess() {
//        // Given
//        AddMemberRequest request = new AddMemberRequest();
//        request.setEmail("test@example.com");
//        request.setPassword("password123");
//        request.setNickname("testuser");
//        request.setAge(25);
//        request.setGender("Male");
//        request.setPreferredGenre("Rock");
//        request.setProfile("profile.png");
//
//        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//        when(memberRepository.findByNickname(request.getNickname())).thenReturn(Optional.empty());
//        when(bCryptPasswordEncoder.encode(request.getPassword())).thenReturn("encryptedPassword");
//
//        Member savedMember = Member.builder()
//                .id(1L)
//                .email(request.getEmail())
//                .password("encryptedPassword")
//                .nickname(request.getNickname())
//                .age(request.getAge())
//                .gender(request.getGender())
//                .preferredGenre(request.getPreferredGenre())
//                .profile(request.getProfile())
//                .isBlocked(Blocked.F)
//                .role(Role.USER.name())
//                .build();
//
//        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);
//
//        // When
//        Member result = memberService.join(request);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("test@example.com", result.getEmail());
//        assertEquals("encryptedPassword", result.getPassword());
//        assertEquals("testuser", result.getNickname());
//        assertEquals(25, result.getAge());
//        assertEquals("Male", result.getGender());
//        assertEquals("Rock", result.getPreferredGenre());
//        assertEquals("profile.png", result.getProfile());
//        assertEquals(Blocked.F, result.getIsBlocked());
//        assertEquals(Role.USER.name(), result.getRole());
//
//        verify(memberRepository, times(1)).findByEmail("test@example.com");
//        verify(memberRepository, times(1)).findByNickname("testuser");
//        verify(bCryptPasswordEncoder, times(1)).encode("password123");
//        verify(memberRepository, times(1)).save(any(Member.class));
//    }
//
//    @Test
//    @DisplayName("사용자 가입 실패 - 이메일 중복")
//    void testJoinFailure_EmailDuplicate() {
//        // Given
//        AddMemberRequest request = new AddMemberRequest();
//        request.setEmail("test@example.com");
//        request.setPassword("password123");
//        request.setNickname("testuser");
//        request.setAge(25);
//        request.setGender("Male");
//        request.setPreferredGenre("Rock");
//        request.setProfile("profile.png");
//
//        Member existingMember = Member.builder()
//                .id(1L)
//                .email(request.getEmail())
//                .password("encryptedPassword")
//                .nickname("existingUser")
//                .age(30)
//                .gender("Female")
//                .preferredGenre("Pop")
//                .profile("existing_profile.png")
//                .isBlocked(Blocked.F)
//                .role(Role.USER.name())
//                .build();
//
//        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingMember));
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            memberService.join(request);
//        });
//
//        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
//
//        verify(memberRepository, times(1)).findByEmail("test@example.com");
//        verify(memberRepository, never()).findByNickname(anyString());
//        verify(bCryptPasswordEncoder, never()).encode(anyString());
//        verify(memberRepository, never()).save(any(Member.class));
//    }
//
//    @Test
//    @DisplayName("사용자 가입 실패 - 닉네임 중복")
//    void testJoinFailure_NicknameDuplicate() {
//        // Given
//        AddMemberRequest request = new AddMemberRequest();
//        request.setEmail("test@example.com");
//        request.setPassword("password123");
//        request.setNickname("testuser");
//        request.setAge(25);
//        request.setGender("Male");
//        request.setPreferredGenre("Rock");
//        request.setProfile("profile.png");
//
//        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//
//        Member existingMember = Member.builder()
//                .id(2L)
//                .email("existing@example.com")
//                .password("encryptedPassword")
//                .nickname(request.getNickname())
//                .age(30)
//                .gender("Female")
//                .preferredGenre("Pop")
//                .profile("existing_profile.png")
//                .isBlocked(Blocked.F)
//                .role(Role.USER.name())
//                .build();
//
//        when(memberRepository.findByNickname(request.getNickname())).thenReturn(Optional.of(existingMember));
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            memberService.join(request);
//        });
//
//        assertEquals("이미 존재하는 닉네임입니다.", exception.getMessage());
//
//        verify(memberRepository, times(1)).findByEmail("test@example.com");
//        verify(memberRepository, times(1)).findByNickname("testuser");
//        verify(bCryptPasswordEncoder, never()).encode(anyString());
//        verify(memberRepository, never()).save(any(Member.class));
//    }
//}