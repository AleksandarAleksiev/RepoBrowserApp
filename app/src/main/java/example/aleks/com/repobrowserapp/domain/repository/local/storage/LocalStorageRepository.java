package example.aleks.com.repobrowserapp.domain.repository.local.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by aleks on 06/05/2018.
 */

public class LocalStorageRepository implements ILocalStorageRepository {

    @NonNull
    private final File cacheFolder;
    @NonNull
    private final Gson gson;

    @Inject
    public LocalStorageRepository(@NonNull File folder,
                                  @NonNull Gson gson) {
        this.cacheFolder = folder;
        this.gson = gson;
    }

    //region properties
    private static final String TAG = LocalStorageRepository.class.getSimpleName();
    private static final String userRepositoriesCacheFile = TAG + "_repositories.json";
    //endregion

    //region ILocalStorageRepository implementation
    @Override
    public Single<UserRepositories> addUserRepositoriesToCache(final UserRepositories userRepositories) {

        return Single.fromCallable(new Callable<UserRepositories>() {
            @Override
            public UserRepositories call() throws Exception {
                try {

                    saveObjectToFile(userRepositories, UserRepositories.class, userRepositoriesCacheFile);
                } catch (Exception ex) {

                    ex.printStackTrace();
                }

                return userRepositories;
            }
        });
    }

    @Override
    public Single<UserRepositories> getUserRepositoriesFromCache() {

        return Single.create(new SingleOnSubscribe<UserRepositories>() {

            @Override
            public void subscribe(SingleEmitter<UserRepositories> emitter) throws Exception {

                UserRepositories repositories = null;

                try {

                    repositories = restoreObjectFromFile(UserRepositories.class, userRepositoriesCacheFile);

                } catch (Exception ex){

                    ex.printStackTrace();
                }

                if (!emitter.isDisposed()) {

                    if (repositories == null) {
                        emitter.onSuccess(UserRepositories.empty());
                    } else {
                        emitter.onSuccess(repositories);
                    }
                }
            }
        });
    }

    @Override
    public void dropUserRepositoriesCache() {

        try {

            deleteFile(userRepositoriesCacheFile);
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
    //endregion

    //region private methods
    private void saveObjectToFile(Object src, Type typeOfSrc, String fileName) throws IOException {

        String jsonString = gson.toJson(src, typeOfSrc);
        overwriteTextFile(fileName, jsonString);
    }

    private <T> T restoreObjectFromFile(Type typeOfSrc, String fileName) throws IOException {

        final String jsonString = readTextFile(fileName);
        return gson.fromJson(jsonString, typeOfSrc);
    }

    private File createNewOrOverwrite(@NonNull String folderName, @NonNull String fileName) throws IOException {

        final File file = new File(folderName, fileName);

        if (file.exists())
            file.delete();

        file.createNewFile();
        return file;

    }

    private void deleteFile(String fileName) {

        final File file = new File(cacheFolder.getAbsolutePath(), fileName);

        if (file.exists())
            file.delete();
    }

    private void overwriteTextFile(@NonNull String fileName, String textToWrite) throws IOException {

        Writer output = null;

        try {
            File file = createNewOrOverwrite(cacheFolder.getAbsolutePath(), fileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(textToWrite);
        } finally {

            if (output != null) {
                output.close();
            }
        }
    }

    @Nullable
    private String readTextFile(@NonNull String fileName) throws IOException {

        InputStream inputStream = null;
        String textContent = null;

        try {

            final File file = new File(cacheFolder.getAbsolutePath(), fileName);

            if (file.exists()) {

                inputStream = new FileInputStream(file);
                final int size = inputStream.available();
                final byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                textContent = new String(buffer, Charset.forName("UTF-8"));
            }
        } finally {

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return textContent;
    }
    //endregion
}
