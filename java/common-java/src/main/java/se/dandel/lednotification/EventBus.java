package se.dandel.lednotification;

import rx.Observable;
import rx.subjects.PublishSubject;

import javax.inject.Singleton;

@Singleton
public class EventBus {
    private PublishSubject<Event> subject = PublishSubject.create();

    public Observable<Event> getEvents() {
        return subject;
    }

    public void send(Event object) {
        subject.onNext(object);
    }
}
