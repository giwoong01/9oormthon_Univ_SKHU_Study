import { useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function SocialLogin() {
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");
    const provider = localStorage.getItem("provider");

    if (code) {
      getToken(code, provider);
    }
  }, [navigate]);

  const kakaoHandleLogin = () => {
    localStorage.setItem("provider", "kakao");
    window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}`;
  };

  const googleHandleLogin = () => {
    localStorage.setItem("provider", "google");
    window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.REACT_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&response_type=code&scope=email profile`;
  };

  const getToken = async (authCode, provider) => {
    try {
      const tokenResponse = await axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/${provider}/token?code=${authCode}`
      );
      const token = tokenResponse.data.data;
      console.log(token);

      navigate("/home");
    } catch (error) {
      console.error("실패! : ", error);
    }
  };

  return (
    <>
      <button onClick={kakaoHandleLogin}>카카오 로그인</button>
      <button onClick={googleHandleLogin}>구글 로그인</button>
    </>
  );
}
