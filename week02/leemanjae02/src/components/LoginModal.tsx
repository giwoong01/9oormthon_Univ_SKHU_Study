import { useState } from "react";
import styles from "../styles/LoginModal.module.less";

interface ModalProps {
  setModalStatus: (ModalStatus: boolean) => void;
}
const LoginModal: React.FunctionComponent<ModalProps> = ({
  setModalStatus,
}) => {
  const clickExitBtn = () => {
    setModalStatus(false);
  };

  const socialKaKaoLogin = () => {
    window.localStorage.setItem("provider", "kakao");
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${
      import.meta.env.VITE_KAKAO_REST_API_KEY
    }&redirect_uri=${
      import.meta.env.VITE_KAKAO_REDIRECT_URI
    }&response_type=code`;
    window.location.href = kakaoURL;
  };

  const socialGoogleLogin = () => {
    window.localStorage.setItem("provider", "google");
    const googleURL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${
      import.meta.env.VITE_GOOGLE_REST_API_KEY
    }&redirect_uri=${
      import.meta.env.VITE_GOOGLE_REDIRECT_URI
    }&response_type=code&scope=email profile`;
    window.location.href = googleURL;
  };
  const [showPassword, setShowPassword] = useState<boolean>(false);

  const clickShowBtn = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className={styles.loginBox}>
      <div className={styles.innerBox}>
        <div className={styles.top}>
          <div className={styles.delBtn} onClick={clickExitBtn}>
            X
          </div>
          <div className={styles.logo}>
            <img src="../public/img/logo.png" />
          </div>
        </div>
        <div className={styles.signupBox}>
          <div className={styles.inputContainer}>
            <div className={styles.inputBox}>
              <input type="text" placeholder="이메일" />
            </div>
            <div className={styles.inputBox}>
              <input
                type={showPassword ? "test" : "password"}
                placeholder="비밀번호"
              />
              <span className={styles.showBtn} onClick={clickShowBtn}>
                {showPassword ? (
                  <img src="../../public/img/hide.png" />
                ) : (
                  <img src="../../public/img/show.png" />
                )}
              </span>
            </div>
          </div>
          <button className={styles.loginBtn} type="submit">
            로그인
          </button>
          <div className={styles.linkBox}>
            <p className={styles.linkItem}>
              <a href="#">아이디(이메일) 찾기</a> <span> | </span>
              <a href="#">비밀번호 찾기</a> <span> | </span>
              <a href="#">회원가입</a>
            </p>
          </div>
        </div>
        <div className={styles.socialBox}>
          <hr className={styles.socialLine}></hr>
          <span>간편로그인</span>
          <div className={styles.socialItem}>
            <div className={styles.kakao} onClick={socialKaKaoLogin}>
              <img src="../../public/img/kakao.png" />
            </div>
            <div className={styles.google} onClick={socialGoogleLogin}>
              <img src="../../public/img/google.png" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginModal;
