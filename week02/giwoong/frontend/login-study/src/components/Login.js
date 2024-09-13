import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
  FormContainer,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
} from "../css/login";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/general/login`,
        {
          email: email,
          pwd: password,
        }
      );
      const token = response.data.data;
      console.log(token);

      navigate("/home");
    } catch (error) {
      console.log("실패! : ", error);
    }
  };

  return (
    <FormContainer>
      <h2>로그인</h2>
      <Form onSubmit={handleLogin}>
        <FormGroup>
          <Label>Email:</Label>
          <Input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </FormGroup>
        <FormGroup>
          <Label>Password:</Label>
          <Input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </FormGroup>
        <Button type="submit">로그인</Button>
        <Button onClick={() => navigate("/signup")}>회원가입 하러가기</Button>
      </Form>
    </FormContainer>
  );
}
