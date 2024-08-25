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

export default function Signup() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSignup = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/general/join`,
        {
          email: email,
          pwd: password,
        }
      );

      if (response) {
        alert("회원 가입 성공!");
        navigate("/");
      }
    } catch (error) {
      console.log("실패! : ", error);
    }
  };

  return (
    <FormContainer>
      <h2>회원가입</h2>
      <Form onSubmit={handleSignup}>
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
        <Button type="submit">회원가입</Button>
      </Form>
    </FormContainer>
  );
}
