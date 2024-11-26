"use client"

import {
  Card,
  Flex,
  Input,
  Link,
  Stack,
} from "@chakra-ui/react";
import {Button} from "@/components/ui/button";
import {Field} from "@/components/ui/field";
import useServices from "@lib/hooks/service-provider";
import useSession from "@lib/hooks/session-provider";
import {useState} from "react";
import {PasswordInput} from "@/components/ui/password-input";
import {toaster} from "@/components/ui/toaster";
import {getRequestError} from "@lib/utils/request-utils";

export default function LoginPage() {
  const { authService } = useServices();
  const { setAccessToken } = useSession();

  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  function handleLogin() {
    authService.login({ userLoginRequestDto: { email, password } })
      .then(response => {
        setAccessToken(response.accessToken ?? null);
        toaster.create({
          title: "Login was successful",
          type: "success"
        });
      })
      .catch(e => toaster.create({
        title: "Login failed",
        description: getRequestError(e),
        type: "error"
      }));
  }

  return (
    <Flex height="100vh" justifyContent="center" alignItems="center">
      <Card.Root maxW="sm">
        <Card.Header>
          <Card.Title>Sign in</Card.Title>
          <Card.Description>
            Fill in the form below to log in
            <Link href="/register" textStyle="sm">Don't have an account yet?</Link>
          </Card.Description>
        </Card.Header>
        <Card.Body>
          <Stack gap="4" w="full">
            <Field label="Email">
              <Input value={email} onChange={e => setEmail(e.target.value)} />
            </Field>
            <Field label="Password">
              <PasswordInput value={password} onChange={e => setPassword(e.target.value)} />
            </Field>
          </Stack>
        </Card.Body>
        <Card.Footer justifyContent="flex-end">
          <Button variant="outline">Cancel</Button>
          <Button variant="solid" onClick={handleLogin}>Sign in</Button>
        </Card.Footer>
      </Card.Root>
    </Flex>
  );
}
