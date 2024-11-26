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

const registerOptions = [
  { title: "Traveler", value: "traveler", icon: <RiSuitcase3Line /> },
  { title: "Hotel Owner", value: "hotel_owner", icon: <RiHotelLine /> }
];

export default function RegisterPage() {
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
              <Input />
            </Field>
            <Field label="Email">
              <Input />
            </Field>
            <Field label="Password">
              <Input />
            </Field>
            <RadioCardRoot
              orientation="horizontal"
              align="center"
              justify="center"
              maxW="lg"
              defaultValue="traveler"
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
          <Button variant="outline">Cancel</Button>
          <Button variant="solid">Sign in</Button>
        </Card.Footer>
      </Card.Root>
    </Flex>
  );
}
