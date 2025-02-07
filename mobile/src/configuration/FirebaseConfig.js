import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
    apiKey: "AIzaSyDu4pmp7h5kCoFl_7HvemJGACa2-kGhptw",
    authDomain: "crytpo-f999a.firebaseapp.com",
    projectId: "crytpo-f999a",
    storageBucket: "crytpo-f999a.firebasestorage.app",
    messagingSenderId: "102824952338",
    appId: "1:102824952338:web:9915f697aae10f11840ecb",
    measurementId: "G-H7HL9QFP12"
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

export { auth };
