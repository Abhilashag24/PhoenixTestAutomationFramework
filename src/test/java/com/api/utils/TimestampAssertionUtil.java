package com.api.utils;

import static org.testng.Assert.fail;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimestampAssertionUtil {
	 private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;

	    /**
	     * Safely parses any timestamp:
	     * - ISO-8601 full timestamps
	     * - yyyy-MM-dd (converted to midnight UTC)
	     */
	 public static Instant parseTimestamp(String ts) {
		    try {
		        // Full ISO timestamp
		        return Instant.parse(ts);
		    } catch (DateTimeParseException ignored1) {

		        // Case: yyyy-MM-ddTHH:mmZ  (missing seconds)
		        if (ts.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}Z")) {
		            return Instant.parse(ts.replace("Z", ":00Z"));
		        }

		        // Case: yyyy-MM-dd (date only)
		        try {
		            LocalDate date = LocalDate.parse(ts);
		            return date.atStartOfDay(ZoneOffset.UTC).toInstant();
		        } catch (DateTimeParseException ignored2) {
		            throw new IllegalArgumentException("Unsupported timestamp format: " + ts);
		        }
		    }
		}


	    /**
	     * Compare two timestamps with tolerance.
	     */
	    public static void assertTimestampEquals(
	            String expected,
	            String actual,
	            Duration tolerance
	    ) {
	        Instant exp = parseTimestamp(expected);
	        Instant act = parseTimestamp(actual);

	        long diffMillis = Math.abs(Duration.between(exp, act).toMillis());

	        if (diffMillis > tolerance.toMillis()) {
	            fail("\nTimestamp mismatch\n" +
	                    "Expected: " + exp + "\n" +
	                    "Actual:   " + act + "\n" +
	                    "Diff(ms): " + diffMillis + " > allowed " + tolerance.toMillis());
	        }
	    }

	    /**
	     * Convenience: exact match (no tolerance)
	     */
	    public static void assertTimestampEqualsExact(String expected, String actual) {
	        assertTimestampEquals(expected, actual, Duration.ZERO);
	    }
	}