import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import SocialLogin from "./components/SocialLogin";
import Home from "./components/Home";
import Signup from "./components/Signup";
import Login from "./components/Login";

export default function App() {
  return (
    <>
      <Router>
        <Routes>
          {/* 일반 자체 로그인 */}
          <Route path="/" element={<Login />} />

          {/* 일반 자체 회원가입 */}
          <Route path="/signup" element={<Signup />} />

          {/* 소셜 로그인 */}
          <Route path="/login" element={<SocialLogin />} />

          {/* 로그인 후 이동 */}
          <Route path="/home" element={<Home />} />
        </Routes>
      </Router>
    </>
  );
}
