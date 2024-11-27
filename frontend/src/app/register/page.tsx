"use client"

import {
  Card,
  Flex,
  HStack, Icon,
  Input,
  Link,
  Stack,
} from "@chakra-ui/react";
import {
  RadioCardItem,
  RadioCardLabel,
  RadioCardRoot,
} from "@/components/ui/radio-card";
import {Button} from "@/components/ui/button";
import {Field} from "@/components/ui/field";
import { RiSuitcase3Line, RiHotelLine } from "react-icons/ri";
import {PasswordInput} from "@/components/ui/password-input";
import {useState} from "react";
import {UserRegisterRequestDtoRegisterTypeEnum} from "@api/models/UserRegisterRequestDto";
import useServices from "@lib/hooks/service-provider";
import useSession from "@lib/hooks/session-provider";
import {toaster} from "@/components/ui/toaster";
import {getRequestError} from "@lib/utils/request-utils";
import {useRouter} from "next/navigation";

const registerOptions = [
  { title: "Traveler", value: UserRegisterRequestDtoRegisterTypeEnum.Traveler, icon: <RiSuitcase3Line /> },
  { title: "Hotel Owner", value: UserRegisterRequestDtoRegisterTypeEnum.HotelOwner, icon: <RiHotelLine /> }
];

export default function RegisterPage() {
  const { authService } = useServices();
  const { setAccessToken } = useSession();
  const router = useRouter();
  
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [registerType, setRegisterType] = useState<UserRegisterRequestDtoRegisterTypeEnum>(UserRegisterRequestDtoRegisterTypeEnum.Traveler);

  function handleRegister() {
    authService.register({ userRegisterRequestDto: { name, email, password, registerType } })
      .then(response => {
        setAccessToken(response.accessToken ?? null);
        toaster.create({
          title: "Registration was successful",
          type: "success"
        });
        router.back();
      })
      .catch(e => toaster.create({
        title: "Registration failed",
        description: getRequestError(e),
        type: "error"
      }));
  }

  return (
    <Flex height="100vh" justifyContent="center" alignItems="center">
      <Card.Root maxW="sm">
        <Card.Header>
          <Card.Title>Sign up</Card.Title>
          <Card.Description>
            Fill in the form below to create an account
            <Link href="/login" textStyle="sm">Already have an account?</Link>
          </Card.Description>
        </Card.Header>
        <Card.Body>
          <Stack gap="4" w="full">
            <Field label="Name">
              <Input value={name} onChange={e => setName(e.target.value)} />
            </Field>
            <Field label="Email">
              <Input value={email} onChange={e => setEmail(e.target.value)} />
            </Field>
            <Field label="Password">
              <PasswordInput value={password} onChange={e => setPassword(e.target.value)} />
            </Field>
            <RadioCardRoot
              orientation="horizontal"
              align="center"
              justify="center"
              maxW="lg"
              value={registerType}
              onValueChange={e => setRegisterType(e.value as UserRegisterRequestDtoRegisterTypeEnum)}
            >
              <RadioCardLabel>Usage Scenario</RadioCardLabel>
              <HStack align="stretch">
                {registerOptions.map((item) => (
                  <RadioCardItem
                    label={item.title}
                    icon={
                      <Icon fontSize="2xl" color="fg.subtle">
                        {item.icon}
                      </Icon>
                    }
                    indicator={false}
                    key={item.value}
                    value={item.value}
                  />
                ))}
              </HStack>
            </RadioCardRoot>
          </Stack>
        </Card.Body>
        <Card.Footer justifyContent="flex-end">
          <Button variant="outline" onClick={_ => router.back()}>Cancel</Button>
          <Button variant="solid" onClick={handleRegister}>Sign in</Button>
        </Card.Footer>
      </Card.Root>
    </Flex>
  );
}
