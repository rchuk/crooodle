import {FetchError, JSONApiResponse, ResponseError} from "@api/runtime";
import {PublicErrorDtoFromJSON, PublicValidationErrorDtoFromJSON} from "@api/models";

export async function getRequestError(reason: unknown): Promise<string> {
  if (reason instanceof ResponseError) {
    if (reason.response.status === 400) {
      const json = new JSONApiResponse(reason.response, PublicValidationErrorDtoFromJSON);
      const publicError = await json.value();

      const validationErrors = Object.entries(publicError?.errors ?? {})
        .map(([key, value]) => `${key}: ${value}`)
        .join(". ");

      if (publicError.message === undefined)
        return "Unknown error";

      return publicError.message + ". " + validationErrors;
    } else {
      const json = new JSONApiResponse(reason.response, PublicErrorDtoFromJSON);
      const publicError = await json.value();

      return publicError.message ?? "Unknown error";
    }
  } else if (reason instanceof FetchError) {
    return "Couldn't fetch data";
  }

  if (reason instanceof Object)
    return reason.toString();

  return "Unknown error";
}
