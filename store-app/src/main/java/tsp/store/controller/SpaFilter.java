package tsp.store.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Single Page Application Filter
 * <p>
 * This filter will rewrite incoming requests to "/"
 * if they are not for /api/** or /assets/**.
 */
@Slf4j
@Component
public class SpaFilter extends OncePerRequestFilter {
    private static final String ASSETS = "/assets/";
    private static final String BASE_URL_API = "/api";
    private static final String BASE_URL_ROOT = "/index.html";

    private static boolean isAsset(String name) {
        return name.startsWith(ASSETS) && name.length() > ASSETS.length();
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        String path = request.getRequestURI();
        boolean forApi = path.startsWith(BASE_URL_API);
        boolean forAssets = isAsset(path);
        log.debug("got a request {} {}, and it is for api: {}, for asset: {}", request.getMethod(), path, forApi, forAssets);
        if (forApi || forAssets)
            filterChain.doFilter(request, response);
        else
            filterChain.doFilter(new IndexHtmlWrapper(request), response);
    }

    private static class IndexHtmlWrapper extends HttpServletRequestWrapper {
        public IndexHtmlWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getRequestURI() {
            return BASE_URL_ROOT;
        }
    }
}
