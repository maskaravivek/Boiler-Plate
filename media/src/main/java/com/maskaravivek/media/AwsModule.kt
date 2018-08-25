package com.maskaravivek.media

import android.app.Application
import android.content.Context
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AwsModule {

    private val identityPoolId: String = "DUMMY"

    @Provides
    @Singleton
    fun provideCognitoCachingCredentialsProvider(context: Context): CognitoCachingCredentialsProvider {
        return CognitoCachingCredentialsProvider(
                context,
                identityPoolId, // Identity Pool ID
                Regions.US_EAST_1 // Region
        )
    }

    @Provides
    @Singleton
    fun provideAmazonS3Client(cognitoCachingCredentialsProvider: CognitoCachingCredentialsProvider): AmazonS3 {
        val amazonS3Client = AmazonS3Client(cognitoCachingCredentialsProvider)
        amazonS3Client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1))
        return amazonS3Client
    }

    @Provides
    @Singleton
    fun provideTransferUtility(s3Client: AmazonS3, context: Context): TransferUtility {
        return TransferUtility(s3Client, context)
    }
}
