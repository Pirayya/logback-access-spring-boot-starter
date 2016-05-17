package net.rakugakibox.springbootext.logback.access.test;

import ch.qos.logback.access.spi.IAccessEvent;
import static ch.qos.logback.access.spi.IAccessEvent.NA;
import static ch.qos.logback.access.spi.IAccessEvent.SENTINEL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * The assertion of {@link IAccessEvent}.
 */
public class AccessEventAssert<S extends AccessEventAssert<S, A>, A extends IAccessEvent> extends AbstractAssert<S, A> {

    /**
     * Constructs an instance.
     *
     * @param actual the actual event.
     */
    protected AccessEventAssert(A actual) {
        super(actual, AccessEventAssert.class);
    }

    /**
     * Constructs an instance.
     *
     * @param actual the actual event.
     * @param selfClass the self class.
     */
    protected AccessEventAssert(A actual, Class<?> selfClass) {
        super(actual, selfClass);
    }

    /**
     * Verifies that the timestamp is in given range.
     *
     * @param start the start value of range (inclusive).
     * @param end the end value of range (inclusive).
     * @return this instance.
     */
    public S hasTimestamp(LocalDateTime start, LocalDateTime end) {
        long actualTimestampAsLong = actual.getTimeStamp();
        Assertions.assertThat(actualTimestampAsLong)
                .isNotEqualTo(SENTINEL);
        LocalDateTime actualTimestamp = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(actualTimestampAsLong), ZoneId.systemDefault());
        Assertions.assertThat(actualTimestamp)
                .isAfterOrEqualTo(start)
                .isBeforeOrEqualTo(end);
        return myself;
    }

    /**
     * Verifies that the protocol is equal to the given one.
     *
     * @param protocol the protocol.
     * @return this instance.
     */
    public S hasProtocol(String protocol) {
        String actualProtocol = actual.getProtocol();
        Assertions.assertThat(actualProtocol)
                .isNotEqualTo(NA)
                .isEqualTo(protocol);
        return myself;
    }

    /**
     * Verifies that the method is equal to the given one.
     *
     * @param method the method.
     * @return this instance.
     */
    public S hasMethod(HttpMethod method) {
        String actualMethod = actual.getMethod();
        Assertions.assertThat(actualMethod)
                .isNotEqualTo(NA)
                .isEqualTo(method.name());
        return myself;
    }

    /**
     * Verifies that the request URI is equal to the given one.
     *
     * @param requestUri the request URI.
     * @return this instance.
     */
    public S hasRequestUri(String requestUri) {
        String actualRequestUri = actual.getRequestURI();
        Assertions.assertThat(actualRequestUri)
                .isNotEqualTo(NA)
                .isEqualTo(requestUri);
        return myself;
    }

    /**
     * Verifies that the request URL (first line of the request) is equal to the given one.
     *
     * @param method the method.
     * @param requestUri the request URI.
     * @param protocol the protocol.
     * @return this instance.
     */
    public S hasRequestUrl(HttpMethod method, String requestUri, String protocol) {
        return hasRequestUrl(method, requestUri, null, protocol);
    }

    /**
     * Verifies that the request URL (first line of the request) is equal to the given one.
     *
     * @param method the method.
     * @param requestUri the request URI.
     * @param query the query string.
     * @param protocol the protocol.
     * @return this instance.
     */
    public S hasRequestUrl(HttpMethod method, String requestUri, String query, String protocol) {
        String actualRequestUrl = actual.getRequestURL();
        Assertions.assertThat(actualRequestUrl)
                .isNotEqualTo(NA)
                .isEqualTo(method.name() + " " + requestUri + (query != null ? "?" + query : "") + " " + protocol);
        return myself;
    }

    /**
     * Verifies that the remote address is equal to the given one.
     *
     * @param remoteAddr the remote address.
     * @return this instance.
     */
    public S hasRemoteAddr(String remoteAddr) {
        String actualRemoteAddr = actual.getRemoteAddr();
        Assertions.assertThat(actualRemoteAddr)
                .isNotEqualTo(NA)
                .isEqualTo(remoteAddr);
        return myself;
    }

    /**
     * Verifies that the local port is equal to the given one.
     *
     * @param localPort the local port.
     * @return this instance.
     */
    public S hasLocalPort(int localPort) {
        int actualLocalPort = actual.getLocalPort();
        Assertions.assertThat(actualLocalPort)
                .isNotEqualTo(SENTINEL)
                .isEqualTo(localPort);
        return myself;
    }

    /**
     * Verifies that the remote host is equal to the given one.
     *
     * @param remoteHost the remote host.
     * @return this instance.
     */
    public S hasRemoteHost(String remoteHost) {
        String actualRemoteHost = actual.getRemoteHost();
        Assertions.assertThat(actualRemoteHost)
                .isNotEqualTo(NA)
                .isEqualTo(remoteHost);
        return myself;
    }

    /**
     * Verifies that the remote user is equal to the given one.
     *
     * @param remoteUser the remote user.
     * @return this instance.
     */
    public S hasRemoteUser(String remoteUser) {
        String actualRemoteUser = actual.getRemoteUser();
        Assertions.assertThat(actualRemoteUser)
                .isNotEqualTo(NA)
                .isEqualTo(remoteUser);
        return myself;
    }

    /**
     * Verifies that the status code is equal to the given one.
     *
     * @param status the status.
     * @return this instance.
     */
    public S hasStatusCode(HttpStatus status) {
        int actualStatusCode = actual.getStatusCode();
        Assertions.assertThat(actualStatusCode)
                .isNotEqualTo(SENTINEL)
                .isEqualTo(status.value());
        return myself;
    }

    /**
     * Verifies that the content length is greater than or equal to the given one.
     *
     * @param contentLength the minimal content length.
     * @return this instance.
     */
    public S hasContentLength(long contentLength) {
        long actualContentLength = actual.getContentLength();
        Assertions.assertThat(actualContentLength)
                .isNotEqualTo(SENTINEL)
                .isGreaterThanOrEqualTo(contentLength);
        return myself;
    }

    /**
     * Verifies that the thread name is available.
     *
     * @return this instance.
     */
    public S hasThreadName() {
        String actualThreadName = actual.getThreadName();
        Assertions.assertThat(actualThreadName)
                .isNotEqualTo(IAccessEvent.NA)
                .isNotEmpty();
        return myself;
    }

    /**
     * Starts the assertion.
     *
     * @param <A> the actual event type.
     * @param actual the actual event.
     * @return an assertion instance.
     */
    public static <A extends IAccessEvent> AccessEventAssert<?, A> assertThat(A actual) {
        return new AccessEventAssert<>(actual);
    }

}
