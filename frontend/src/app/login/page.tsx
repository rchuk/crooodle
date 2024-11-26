import {
  Card,
  Flex,
  Input,
  Link,
  Stack,
} from "@chakra-ui/react";
import {Button} from "@/components/ui/button";
import {Field} from "@/components/ui/field";

export default function LoginPage() {
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
              <Input />
            </Field>
            <Field label="Password">
              <Input />
            </Field>
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
