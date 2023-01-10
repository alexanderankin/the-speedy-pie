package tsp.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Single Page Application Filter
 * <p>
 * This filter will rewrite incoming requests to "/"
 * if they are not for /api/** or /assets/**.
 */
@Slf4j
@Component
public class SpaFilter implements WebFilter {
    private static final String ASSETS = "/assets/";
    private static final String BASE_URL_API = "/api";
    private static final String BASE_URL_ROOT = "/";

    private static boolean isAsset(String name) {
        return name.startsWith(ASSETS) && name.length() > ASSETS.length();
    }

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange,
                             @NonNull WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        boolean forApi = path.startsWith(BASE_URL_API);
        boolean forAssets = isAsset(path);
        log.debug("got a request {} {}, and it is for api: {}, for asset: {}", exchange.getRequest().getMethod(), path, forApi, forAssets);
        if (forApi || forAssets)
            return chain.filter(exchange);

        ServerHttpRequest req = exchange.getRequest().mutate().path(BASE_URL_ROOT).build();
        return chain.filter(exchange.mutate().request(req).build());
    }
}
