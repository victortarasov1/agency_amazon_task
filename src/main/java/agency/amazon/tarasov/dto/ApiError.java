package agency.amazon.tarasov.dto;

import java.util.List;

public record ApiError(String message, List<String> debugMessage) {}