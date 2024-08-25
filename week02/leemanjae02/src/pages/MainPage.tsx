import { useEffect, useState } from "react";
import styles from "../styles/MainPage.module.less";
import LoginModal from "../components/LoginModal";
import axios from "axios";

const MainPage = () => {
  const [modalStatus, setModalStatus] = useState<boolean>(false);
  const [userNickName, setUserNickName] = useState<string>("");
  useEffect(() => {
    const provider = window.localStorage.getItem("provider");
    const token = window.localStorage.getItem("token");
    if (provider === "kakao" && token) {
      getKakaoUserData(token as string)
        .then((data) => {
          setUserNickName(data.properties?.nickname || "사용자 이름 없음");
        })
        .catch((error) => {
          console.log(error);
          window.localStorage.removeItem("token");
        });
    }
    if (provider === "google") {
      getGoogleUserData(token as string)
        .then((data) => {
          setUserNickName(data.name || "사용자 이름 없음");
        })
        .catch((err) => {
          console.log(err);
          window.localStorage.removeItem("token");
        });
    }
  }, []);

  const getKakaoUserData = async (token: string) => {
    const response = await axios.get(`https://kapi.kakao.com/v2/user/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
      },
    });

    return response.data;
  };

  const getGoogleUserData = async (token: string) => {
    const response = await axios.get(
      ` https://www.googleapis.com/oauth2/v3/userinfo`,
      {
        headers: {
          authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;
  };
  const clickLogin = () => {
    setModalStatus(true);
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.headerBox}>
          {userNickName ? (
            <div>{userNickName}</div>
          ) : (
            <p onClick={clickLogin}>로그인</p>
          )}

          <p>회원가입</p>
        </div>
        <div className={styles.mainBox}>
          {modalStatus ? <LoginModal setModalStatus={setModalStatus} /> : ""}
        </div>
      </div>
    </>
  );
};

export default MainPage;
