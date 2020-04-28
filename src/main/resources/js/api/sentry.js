import Vue from 'vue'
import * as Sentry from '@sentry/browser';
import { Vue as VueIntegration } from '@sentry/integrations';

export function initSenty(){
    console.log("sentry initialization")
    Sentry.init({
        dsn: 'https://339d5fa59e26408e94cea1deb6457398@o381691.ingest.sentry.io/5209398',
        integrations: [new VueIntegration({Vue, attachProps: true})],
    })

    Sentry.setUser({
        id: profile && profile.id,
        username: profile && profile.name,
        // email: profile && profile.email,
    });
    Sentry.captureMessage('Sentry was initialized')
}
