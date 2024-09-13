import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";

const AuthPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    const provider = window.localStorage.getItem("provider");
    if (provider === "kakao") {
      getKaKaoToken().then((response) => {
        if (response) {
          localStorage.setItem("token", response.data.access_token);
          navigate("/");
        }
      });
    }
    if (provider === "google") {
      getGoogleToken().then((response) => {
        if (response) {
          localStorage.setItem("token", response.data.access_token);
        }
      });
      navigate("/");
    }
  }, [navigate]);

  const getKaKaoToken = async () => {
    const code = new URLSearchParams(window.location.search).get("code");

    const response = await axios.post(
      "https://kauth.kakao.com/oauth/token",
      {
        grant_type: "authorization_code",
        client_id: import.meta.env.VITE_KAKAO_REST_API_KEY,
        redirect_uri: import.meta.env.VITE_KAKAO_REDIRECT_URI,
        code: code,
      },
      {
        headers: {
          "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
        },
      }
    );
    return response;
  };
  const getGoogleToken = async () => {
    const code = new URLSearchParams(window.location.search).get("code");

    const response = await axios.post(
      "https://oauth2.googleapis.com/token",
      {
        code: code,
        client_id: import.meta.env.VITE_GOOGLE_REST_API_KEY,
        client_secret: import.meta.env.VITE_GOOGLE_SECRET,
        redirect_uri: import.meta.env.VITE_GOOGLE_REDIRECT_URI,
        grant_type: "authorization_code",
      },
      {
        headers: {
          "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
        },
      }
    );

    return response;
  };

  return <div>test</div>;
};

export default AuthPage;
