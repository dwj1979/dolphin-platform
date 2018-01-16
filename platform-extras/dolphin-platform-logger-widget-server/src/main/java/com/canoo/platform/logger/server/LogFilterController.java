package com.canoo.platform.logger.server;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.functional.Subscription;
import com.canoo.platform.logger.model.LogSearchFilterBean;
import com.canoo.platform.logger.model.LoggerSearchRequest;
import com.canoo.platform.remoting.server.RemotingAction;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;
import org.slf4j.event.Level;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@RemotingController
public class LogFilterController {

    @RemotingModel
    private LogSearchFilterBean model;

    private final List<Consumer<LoggerSearchRequest>> searchListener = new ArrayList<>();

    @RemotingAction
    public void search() {
        final LoggerSearchRequest request = getCurrentRequest();
        searchListener.forEach(l -> l.accept(request));
    }

    @RemotingAction
    public void clearStartDate() {
        final LocalDateTime minTime = LocalDateTime.MIN;
        model.setStartDate(ZonedDateTime.of(minTime, ZoneId.systemDefault()));
    }

    @RemotingAction
    public void setStartDateToNow() {
        model.setStartDate(ZonedDateTime.now());
    }

    @RemotingAction
    public void setEndDateToNow() {
        model.setEndDateTime(ZonedDateTime.now());
    }

    @RemotingAction
    public void removeLevelFiltering() {
        model.getLevel().clear();
        model.getLevel().addAll(Level.values());
    }

    public LoggerSearchRequest getCurrentRequest() {
        final ZonedDateTime start = model.getStartDate();
        final ZonedDateTime end = model.getEndDateTime();
        final Set<Level> levels = Collections.unmodifiableSet(new HashSet<>(model.getLevel()));
        final int maxResults = Optional.ofNullable(model.maxResultsProperty().get()).orElse(-1);
        return new LoggerSearchRequest(start, end, levels, maxResults);
    }

    public Subscription addSearchListener(final Consumer<LoggerSearchRequest> listener) {
        searchListener.add(Assert.requireNonNull(listener, "listener"));
        return () -> searchListener.remove(listener);
    }
}
